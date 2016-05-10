let $maisons := "exercice-2/maisons.xml"
return
<html>
	<table border="1">
		<thead>
        	<tr>
               	<th>Maisons</th>
               	<th>Surface (m2)</th>
            </tr>
        </thead>
        <tbody>
   			{ 
   				for $maison in (doc($maisons)//maison)
       			return  
       			<tr>
       				<th>Maison {data($maison/@id)}</th> 
      				<th>{sum(data($maison/child::*/child::*/@surface-m2))}</th>
      			</tr>
			}
		</tbody>
	</table>
</html>