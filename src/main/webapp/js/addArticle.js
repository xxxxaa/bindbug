/**
 * Created by yan on 16/2/1.
 */

function createXMLHttpRequest(){
    var xmlHttp;
    if(window.XMLHttpRequest){
        xmlHttp = new XMLHttpRequest();
        if(xmlHttp.overrideMimeType){
            xmlHttp.overrideMimeType("text/xml")
        }
    }else if(window.ActiveXObject){
        try{
            xmlHttp = new ActiveXObject("Msxml12.XMLHTTP");
        }catch (e){
            try {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }catch (e){}
        }
    }
    return xmlHttp;
}
