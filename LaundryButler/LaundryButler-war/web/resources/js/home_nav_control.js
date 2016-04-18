$(function(){
          $(".subscreen").hide();
          $("#home").show();
          
          $("#nav_logo").click(function(){
            $(".subscreen").hide();
            $("#home").show();
          });
          
          $("#nav_home").click(function(){
            $(".subscreen").hide();
            $("#home").show();
          });
          
          $("#footer_up").click(function(){
            $(".subscreen").hide();
            $("#home").show();
          });
          
          $("#nav_mybox").click(function(){
            $(".subscreen").hide();
            $("#mybox").show();
          });
          
          $("#nav_myinvoice").click(function(){
            $(".subscreen").hide();
            $("#myinvoice").show();
          });
          
          $("#nav_discover").click(function(){
            $(".subscreen").hide();
            $("#discover").show();
          });
          
          $("#nav_freedry").click(function(){
            $(".subscreen").hide();
            $("#freedry").show();
          });
          
          $("#nav_contact").click(function(){
            $(".subscreen").hide();
            $("#contact").show();
            $("#googleMapDiv").show();
            initializeMap();
          }); 
        });