$(function(){
          $(".subscreen").hide();
          $("#employee_boxes").show();
          
          $("#nav_boxes").click(function(){
            $(".subscreen").hide();
            $("#employee_boxes").show();
          });
          
          $("#nav_employees").click(function(){
            $(".subscreen").hide();
            $("#employee_employees").show();
          });
          
          $("#nav_customers").click(function(){
            $(".subscreen").hide();
            $("#employee_customers").show();
          });
          
          $("#nav_transactions").click(function(){
            $(".subscreen").hide();
            $("#employee_transactions").show();
          });
          
          $("#nav_products").click(function(){
            $(".subscreen").hide();
            $("#employee_products").show();
          });
          
          $("#nav_account").click(function(){
            $(".subscreen").hide();
            $("#employee_account").show();
          });
          
        });