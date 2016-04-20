$(function(){
          $(".subscreen").hide();
          $("#mybox").show();
          
          $("#nav_logo").click(function(){
            $(".subscreen").hide();
            $("#mybox").show();
          });
          
          $("#nav_mybox").click(function(){
            $(".subscreen").hide();
            $("#mybox").show();
          });
          
          $("#nav_myaccount").click(function(){
            $(".subscreen").hide();
            $("#myaccount").show();
          });
          
          $("#nav_buy").click(function(){
            $(".subscreen").hide();
            $("#buy").show();
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