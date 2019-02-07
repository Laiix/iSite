define(function(require, exports, module) {
    require.async('highlight', function () {
        hljs.initHighlightingOnLoad();
        $('pre').each(function(i, block) {
            hljs.highlightBlock(block);
        });
    });

    require.async('share', function () {
    });

    $('a[data-toggle="fulltext"]').click(function () {
        $('.topic').addClass('fulltext');
    });

    if ($('.topic').outerHeight(true) <= 700) {
        $('.topic').addClass('fulltext');
    }
});