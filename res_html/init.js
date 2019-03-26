init = initChat(username);
        function initChat(s){



            var textAr = document.getElementById("typeArea");
            var sendButton = document.getElementById("sendButton");
            var empLi = document.getElementsByClassName("emptyLi");
            var popUp1 = document.getElementsByClassName ("popUp")[0];

            hideAllNotices();


            for (i=0; i<empLi.length; i++){
                empLi[i].style.display = "none";
            }

            textAr.style.display = "none";
            sendButton.style.display = "none";
            popUp1.style.display = "none";

            var username = s;
            var ws = null;
            var currentTo = null;


            var getCookie = function (cname){
                var name = cname+"=";
                var decodedCookie = decodeURIComponent(document.cookie);
                var ca = decodedCookie.split(';');
                for (var i=0; i<ca.length; i++){
                    var c = ca[i];
                    while (c.charAt(0)==' '){
                        c = c.substring(1);
                    }
                    if (c.indexOf(name)==0){
                        return c.substring(name.length, c.length);
                    }
                }
                return "";
            }


            var displayMessage = function (message){

                var text = message.body;
                var from = message.from;
                var to = message.to;

               var diva = document.createElement("DIV");
               var divb = document.createElement("DIV");
               var t = document.createTextNode(text);
               var f = document.createTextNode(from);
               diva.appendChild(divb);
               diva.appendChild(t);
               divb.appendChild(f);
               if (message.from == username){
                        diva.className = "me";
                    }
               else {
                        diva.className = "notMe";
                    }
               divb.className = "msgFromHead";
               document.getElementById("chatWindow").appendChild(diva);
               scrollToBottom();


          }

            var scrollToBottom= function (){
                var div = document.getElementById("chatWindow");
                div.scrollTop = div.scrollHeight - div.clientHeight;
            }

            var connect = function(){

                        if (ws==null){
                            ws = new WebSocket("ws://localhost:8080/chat"+"/"+getCookie('token'));

                            ws.onopen = function () {
                                var msg={
                                    type: "LOGIN",
                                    from: username
                                }
                                ws.send(JSON.stringify(msg));
                            }

                            ws.onmessage = function (event) {
                                var msgJSON = event.data;
                                var msg = JSON.parse(msgJSON);
                                if (msg.type=="MESSAGE"){
                                   if (msg.from == currentTo||(msg.to=='all'&&currentTo==msg.to)) {
                                        displayMessage (msg);
                                        console.log(msg.from);
                                   }
                                   else {

                                        console.log("dede "+msg.to);
                                        if (msg.to!='all') {
                                            showNotice (msg);
                                        }
                                   }
                                }
                                else{
                                    console.log ('Invalid message type');
                                }
                            }

                            ws.onclose = function () {
                                ws = null;
                            }
                        }
            }


            var srchBtn = document.getElementsByClassName ("searchButton")[0];

            if (srchBtn!=null){
                srchBtn.onclick = function(){
                    popUp1.style.display = 'block';
                    loadSearchList();
                }
            }


            var clsBtn1 = document.getElementsByClassName ("btn-close")[0];

            clsBtn1 .onclick = function(){
                popUp1.style.display = 'none';
            }

            connect();

            return {

                    sendMessage: function(){

                       var text = document.getElementById("typeArea").value;

                       if (text=='') return;
                        var msg={
                            type: "MESSAGE",
                            from: username,
                            to: currentTo,
                            body: text

                        }
                       document.getElementById("typeArea").value='';

                       ws.send(JSON.stringify(msg));
                       if (msg.to!='all') displayMessage (msg);

                    },

                    selectReciever: function(event){

                        var active;
                        e = document.getElementsByClassName("active");
                        for (i = 0; i < e.length; i++) {
                            e[i].className = e[i].className.replace(" active", "");
                        }

                        var crtTrg = event.currentTarget;
                        to = crtTrg.getAttribute("data-contact-name");
                        if (crtTrg.className=="selectResButton")event.currentTarget.children[0].style.display = "none";

                        if (to=='sendAll') {
                             document.getElementById("reciever").innerHTML = '<i class="fas fa-users"></i> Common chat';
                             document.getElementById("sendAll").innerHTML='You are in common chat';
                             currentTo='all';
                        }
                        else {
                            document.getElementById("sendAll").innerHTML= sendAllBtnValue;
                            currentTo=to;
                            document.getElementById("reciever").innerHTML = to;
                            hideNotice(to);
                        }
                        event.currentTarget.className += " active";
                        textAr.style.display = "block";
                        sendButton.style.display = "block";
                        document.getElementById("chatWindow").innerHTML="";
                        textAr.focus()

                    }
            }

        };





