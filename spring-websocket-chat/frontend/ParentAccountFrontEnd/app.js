var token;
var baseURL = "http://localhost:7789"

console.log("hi");

//window.onload = function() {
//    token = localStorage.getItem('token')
//    if(token){
//        token = JSON.parse(token);
//        console.log(token)
//    }
//}
//
//setInterval(function(){
//    if(token){
//        getParentData();
//    }
//}, 300);

function getParentData(){
     console.log(token);
      token = localStorage.getItem('token')
      if(token){
          token = JSON.parse(token);
          console.log(token)
      }
      token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMTExIFBhcmVudCIsImV4cCI6MTYyMDIxNzU5OSwiaWF0IjoxNjIwMTgxNTk5fQ.AmgaBuSV7Cu4WDdx7F1LVoS4fBYGrsFH2GGLB1AR8L0";
      console.log(token);

      $.ajax({
         url: baseURL +"/parentProfile",
         type: 'GET',
         contentType: 'application/json',
         headers: {
            'Authorization': 'Bearer ' +token
         },
         success: function (result) {
             // CallBack(result);
             console.log(result)
         },
         error: function (error) {

         }
      });
}

getParentData();


