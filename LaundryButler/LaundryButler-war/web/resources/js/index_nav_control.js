$(function(){
          $(".page-div").hide();
          $("#login_jumbotron").show();
          
          $("#nav_logo").click(function(){
            $(".page-div").hide();
            $("#login_jumbotron").show();
          });
          
          $("#footer_up").click(function(){
            $(".page-div").hide();
            $("#login_jumbotron").show();
          });
          
          $("#nav_about").click(function(){
            $(".page-div").hide();
            $("#about").show();
          });
          
          $("#nav_services").click(function(){
            $(".page-div").hide();
            $("#services").show();
          });
          
          $("#nav_discover").click(function(){
            $(".page-div").hide();
            $("#discover").show();
          });
          
          $("#nav_contact").click(function(){
            $(".page-div").hide();
            $("#contact").show();
            $("#googleMap").show();
          }); 
        });