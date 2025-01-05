@import hexlet.code.dto.UrlsPage
@import hexlet.code.util.Routes
@import java.time.format.DateTimeFormatter
@param UrlsPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getAllUrls())
                !{var urlCheck = page.getLatestChecks().get(url.getId());}

                <tr>
                    <td>
                        ${url.getId()}
                    </td>
                    <td>
                        <a href="${Routes.urlsPath()}/${url.getId()}">${url.getName()}</a>
                    </td>
                    <td>
                        ${urlCheck == null ? "" : urlCheck.getCreatedAt().format(formatter)}
                    </td>
                    <td>
                        ${urlCheck == null ? "" : String.valueOf(urlCheck.getStatusCode())}
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`
)