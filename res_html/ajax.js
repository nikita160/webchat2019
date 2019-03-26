      function loadSearchList(){
        clearSearchList();
        document.getElementById("srchResMsg").style.display = "none";
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                if (this.responseText=="") {
                    document.getElementById("srchResMsg").style.display = "block";
                }
                else {
                    updateSearchList(this.responseXML);
                }
            }
        };
        query = document.getElementById ("searchTerm").value.trim();
        xhttp.open("GET", "search?s="+query, true);
        xhttp.send();
      }

     function addContact(event){

        var elem = event.currentTarget.parentNode;
        var name = elem.dataset.contactName;
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState == 4 ) {
                if (xhttp.status!=200) {
                    alert ("Cannot update contact list!");
                }
                else {
                    updateContactList(elem);
                }
            }
        };
        xhttp.open("POST", "add-friend", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("add="+name);
     }


     function updateSearchList(respXML) {

        var list, itm, toCln, i, srchRes, t;

        list = respXML.getElementsByTagName("item");

        srchRes = document.getElementById("searchResultList");

        toCln = document.getElementsByClassName ("emptyLi")[1];

        for (i = 0; i < list.length; i++) {
            itm = list[i].childNodes[0].nodeValue;
            cln = toCln.cloneNode(true);
            cln.classList.remove("emptyLi");
            cln.style.display="block";
            cln.children[0].setAttribute ("data-contact-name", itm);
            t = document.createTextNode(itm);
            cln.children[0].children[0].appendChild(t);
            srchRes.appendChild(cln);
        }
     }


     function clearSearchList(){

        var list, i, l;
        list = document.getElementById("searchResultList");
        i=0;
        while (list.childNodes.length>1){
            if (list.childNodes[0].className=="emptyLi"){
                i=1;
            }
            list.removeChild(list.childNodes[i]);
        }
     }

     function updateContactList(elem){
        var toCln, cln;
        var name = elem.dataset.contactName;
        addElemToContactList (name);
        removeFromSearchList(elem);
        popNotice(elem);


     }


