<script language="javascript" type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
 <script  language="javascript" type="text/javascript"  src="../js/jquery.min.js"></script> 



<form name="para_form" action="./mn" target="_balnk">
<label for="para"> Enter the Text Here :</label>

<input type="text" name="data" id="data" placeholder="Write some thing"/>
<input type="submit" value="Analyze" />

</form>
<%if(request.getParameter("data") !=null){ %>
<br>
<br>
<p>YAGO OutPut <br><%=request.getAttribute("op")  %></p>
<%} %>
