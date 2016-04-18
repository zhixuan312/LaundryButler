$(function(){
          $(".subscreen").hide();
          $("#login_jumbotron").show();
          
          $("#nav_logo").click(function(){
            $(".subscreen").hide();
            $("#login_jumbotron").show();
          });
          
          $("#nav_about").click(function(){
            $(".subscreen").hide();
            $("#about").show();
          });
          
          $("#nav_pricing").click(function(){
            $(".subscreen").hide();
            $("#pricing").show();
          });
          
          $("#nav_contact").click(function(){
            $(".subscreen").hide();
            $("#contact").show();
            $("#googleMapDiv").show();
            initializeMap();
          }); 
        });