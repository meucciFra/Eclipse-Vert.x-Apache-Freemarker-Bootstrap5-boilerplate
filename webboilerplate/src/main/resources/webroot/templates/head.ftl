<#-- This macro manage the head portion of every HTML page of this project including Bootstrap5, custom stylesheet and favicon -->

<#macro head>
<head>
  <title>${sitename!"sitename"}</title>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <meta name="description" content="" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="stylesheet/style.css" />
  <link rel="icon" href="assets/company/favicon.png">
</head>
</#macro>