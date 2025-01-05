@import hexlet.code.dto.UrlPage
@import hexlet.code.util.Routes
@import java.time.format.DateTimeFormatter
@param UrlPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>

        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${page.getUrl().getCreatedAt().format(formatter)}</td>
            </tr>
            </tbody>
        </table>
        <br>
        <br>
        <h2>Проверки</h2>
        <form method="post" action="${Routes.urlChecks(page.getUrl().getId())}">
            <button type="submit" class="h-100 btn btn-sm btn-info px-sm-4">Запустить проверку</button>
        </form>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th class="col-1">Код ответа</th>
                <th class="col-2">title</th>
                <th class="col-1">h1</th>
                <th>description</th>
                <th class="col-2">Дата проверки</th>
            </tr>
            </thead>
            <tbody>
            @for(var check : page.getCheck())

                <tr>
                    <td>
                        ${check.getUrlId()}
                    </td>
                    <td>
                        ${check.getStatusCode()}
                    </td>
                    <td>
                        ${check.getTitle()}
                    </td>
                    <td>
                        ${check.getH1()}
                    </td>
                    <td>
                        ${check.getDescription()}
                    </td>
                    <td>
                        ${check.getCreatedAt().format(formatter)}
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>`
)