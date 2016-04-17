$(function(){
          $(".subscreen").hide();
          $("#login_jumbotron").show();
          
          $("#nav_logo").click(function(){
            $(".subscreen").hide();
            $("#login_jumbotron").show();
          });
          
          $("#nav_login").click(function(){
            $(".subscreen").hide();
            $("#login_jumbotron").show();
          });
          
          $("#footer_up").click(function(){
            $(".subscreen").hide();
            $("#login_jumbotron").show();
          });
          
          $("#nav_about").click(function(){
            $(".subscreen").hide();
            $("#about").show();
          });
          
          $("#nav_services").click(function(){
            $(".subscreen").hide();
            $("#services").show();
          });
          
          $("#nav_discover").click(function(){
            $(".subscreen").hide();
            $("#discover").show();
          });
          
          $("#nav_contact").click(function(){
            $(".subscreen").hide();
            $("#contact").show();
            $("#googleMapDiv").show();
            initializeMap();
          }); 
        });