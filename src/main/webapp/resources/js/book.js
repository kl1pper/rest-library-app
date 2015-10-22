var Book = (function () {
    var contextPath;
    var booksTable;
    var paginator;
    var viewDialog;
    var editDialog;
    var deleteDialog;
    var datepicker;
    var currentSearch;
    var bootpagOptions = {
        total: 10,
        page: 1
    };
    var tableDirectives = {
        books: {
            viewLink: {
                onclick: function () {
                    return 'Book.view(' + this.id + ');';
                }
            },
            editLink: {
                onclick: function () {
                    return 'Book.prepareEdit(' + this.id + ');';
                }
            },
            deleteLink: {
                onclick: function () {
                    return 'Book.prepareRemove(' + this.id + ');';
                }
            }
        }
    };

    var editDialogDirectives = {
        confirmButton: {
            html: function () {
                return this.buttonTitle;
            },
            onclick: function () {
                return this.buttonAction;
            }
        }
    };

    var deleteDialogDirectives = {
        deleteButton: {
            onclick: function () {
                return 'Book.remove(' + this.id + ');';
            }
        },
        message: {
            html: function () {
                return 'Вы действительно хотите удалить книгу "'+ this.title + '"?';
            }
        }
    };

    function load(contextPathNew) {
        contextPath = contextPathNew;
        booksTable = $('#books-table');
        paginator = $('#paginator');
        viewDialog = $('#book-view');
        editDialog = $('#book-edit');
        deleteDialog = $('#book-delete');
        clearSearch();

        datepicker = $('#publishedYear');
        datepicker.datepicker({
            format: "yyyy",
            startView: 2,
            minViewMode: 2
        });
    }

    function refreshTable() {
        $.ajax({
            type: "GET",
            url: contextPath + "/book?page=" + bootpagOptions.page + "&search=" + JSON.stringify(validateBook(currentSearch)),
            contentType: "charset=utf-8",
            async: false,
            success: function (resp) {
                editDialog.modal('hide');
                bootpagOptions.total = resp.pageCount;
                booksTable.render({books: resp.books}, tableDirectives);
            },
            error: function (jqXHR, textStatus, e) {
                showException(textStatus);
            }
        });
    }

    function refreshPaginator() {
        paginator.bootpag(bootpagOptions).on("page", function(event, num){
            bootpagOptions.page = num;
            refreshTable();
        });
    }

    function getById(id) {
        var book = null;
        $.ajax({
            type: "GET",
            url: contextPath + "/book/" + id,
            contentType: "charset=utf-8",
            async: false,
            success: function (resp) {
                book = resp;
            },
            error: function (jqXHR, textStatus, e) {
                showException(textStatus);

            }
        });
        return book;
    }

    function getTitleById(id) {
        var title = null;
        $.ajax({
            type: "GET",
            url: contextPath + "/book/title/" + id,
            contentType: "charset=utf-8",
            async: false,
            success: function (resp) {
                title = resp;
            },
            error: function (jqXHR, textStatus, e) {
                showException(textStatus);

            }
        });
        return title;

    }

    function view(id) {
        var book = getById(id);
        if (book != null) {
            viewDialog.render({book: book});
            viewDialog.modal('show');
        }
    }

    function clearSearch() {
        bootpagOptions.page = 1;
        currentSearch  = {
            author: null,
            title: null,
            publishedYear: null,
            description: null
        };
        refreshTable();
        refreshPaginator();
    }

    function search() {
        bootpagOptions.page = 1;
        currentSearch = fillBook();
        refreshTable();
        refreshPaginator();

    }

    function create() {
        var book = validateBook();

        if (book.author && book.title) {
            $.ajax({
                type: "PUT",
                url: contextPath + "/book",
                contentType: "application/json; charset=utf-8",
                async: false,
                data: JSON.stringify(book),
                success: function () {
                    editDialog.modal('hide');
                    refreshTable();
                },
                error: function (jqXHR, textStatus, e) {
                    showException(textStatus);
                }
            });
        }
    }

    function edit(id) {

        var book = validateBook();

        if (book.author && book.title) {
            book.id = id;
            $.ajax({
                type: "POST",
                url: contextPath + "/book",
                contentType: "application/json; charset=utf-8",
                async: false,
                data: JSON.stringify(book),
                success: function () {
                    editDialog.modal('hide');
                    refreshTable();
                },
                error: function (jqXHR, textStatus, e) {
                    showException(textStatus);
                }
            });
        }
    }

    function remove(id) {
        $.ajax({
            type: "DELETE",
            url: contextPath + "/book/" + id,
            async: false,
            success: function () {
                deleteDialog.modal('hide');
                refreshTable();
            },
            error: function (jqXHR, textStatus, e) {
                showException(textStatus);
            }
        });
    }

    function prepareSearch() {
        var model = {
            modalTitle: 'Поиск',
            buttonTitle: 'Найти',
            buttonAction: 'Book.search();',
            book: currentSearch
        };
        openEditDialog(model);
    }

    function prepareCreate() {
        var model = {
            modalTitle: 'Создание новой книги',
            buttonTitle: 'Создать',
            buttonAction: 'Book.create();',
            book: {
                author: null,
                title: null,
                publishedYear: null,
                description: null
            }
        };
        openEditDialog(model);
    }

    function prepareEdit(id) {
        var book = getById(id);
        if (book != null) {
            var model = {
                modalTitle: 'Редактирование книги',
                buttonTitle: 'Сохранить',
                buttonAction: 'Book.edit(' + id + ');',
                book: book
            };
            openEditDialog(model);
        }
    }

    function prepareRemove(id) {
        deleteDialog.render(
            {id: id, title: getTitleById(id)}, deleteDialogDirectives);
        deleteDialog.modal('show');
    }

    function openEditDialog(model) {
        editDialog.render(model, editDialogDirectives);
        datepicker.datepicker('update');
        editDialog.modal('show');
    }

    function fillBook() {
        return {
            author: $('#author').val(),
            title: $('#title').val(),
            publishedYear: $('#publishedYear').val(),
            description: $('#description').val()
        }
    }

    function validateBook(book) {
        if (book == undefined) {
            book = fillBook();
        }

        book.author = validateValue(book.author);
        book.title = validateValue(book.title);
        book.publishedYear = validateValue(book.publishedYear);
        book.description = validateValue(book.description);

        return book;
    }

    function validateValue(val) {
        if (val != null && val.length > 0) {
            return val;
        } else {
            return undefined;
        }
    }

    function showException(textStatus) {
        var resp;
        try {
            resp = JSON.parse(textStatus.responseText);
            alert(resp.error);
        } catch (e) {
            alert("Произошла неизвестная ошибка на сервере");
        }
    }

    return {
        load: load,
        view: view,
        clearSearch: clearSearch,
        search: search,
        create: create,
        edit: edit,
        remove: remove,
        prepareSearch: prepareSearch,
        prepareCreate: prepareCreate,
        prepareEdit: prepareEdit,
        prepareRemove: prepareRemove
    };
})();
