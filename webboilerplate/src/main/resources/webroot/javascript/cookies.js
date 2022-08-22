"use strict";
(function($){
  $('.i-accept').on('click', function() {
    if(localStorage.noshow !== '1') {
      $('#cookie-notice').addClass('d-none');
       localStorage.noshow='1';
    }
  });
  if(localStorage.noshow == '1') {
    $('#cookie-notice').addClass('d-none');
  };
})(jQuery);