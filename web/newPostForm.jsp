<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <div id="newpostform">
    <h3> Pubblica un nuovo Post</h3>
    <form method="POST" id="NewPost" name="Submit" action="postmanager.html">
        <input type="hidden" name="command" value="add">
        <input type="hidden" name="wallid" value="${sessionScope.visitedWallid}">  
        <input type="hidden" name="isgroup" value="${sessionScope.visitedIsgroup}">        
        <textarea style="resize:none" rows="7" cols="72" maxlength="500" name="msg" form="NewPost">A cosa stai pensando?</textarea> <br>
        <textarea style="resize:none" rows="2" cols="72" maxlength="500" name="url" form="NewPost">Vuoi allegare un link?</textarea> <br>
        <input type="submit" class="submit" value="Pubblica" name="Submit">
        <input type="reset" class="reset" value="Annulla">
    </form>
    </div>
</html>
