@import gg.jte.Content
@import hexlet.code.dto.BasicPage
@import hexlet.code.util.Routes

@param BasicPage page = null
@param Content content

<!doctype html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Анализатор страниц</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</head>

<body class="d-flex flex-column min-vh-100">
<nav class="ml-1 navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand p-2" href="/">Анализатор страниц</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="${Routes.mainPath()}">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${Routes.urlsPath()}">Сайты</a>
            </li>
        </ul>
    </div>
</nav>
<main class="flex-grow-1">
    @if(page != null && page.getFlash() != null)
        <div class="rounded-0 m-0 alert alert-success alert-${page.getFlashType()}" role="alert">
            ${page.getFlash()}
        </div>
    @endif
    ${content}
</main>
<footer class="footer border-top py-1 mt-4 bg-light">
    <p class="text-dark bg-muted text-center">by <a href="https://github.com/DmitryCo" class="text-info"Dmitry Konstantinov</a></p>
</footer>
</body>
</html>