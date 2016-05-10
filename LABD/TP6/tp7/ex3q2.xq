declare function local:plant-by-light($catalog,$searchLight) { 
   for $plant in (doc($catalog)//PLANT)
        where some $light in $plant/LIGHT 
        satisfies ($light = data($searchLight)) 
        return ('&#xa;',<PLANT>{$plant/child::node() except $plant/LIGHT}</PLANT>)
}; 
let $catalog := "exercice-3/plant_catalog.xml"
return
<CATALOG> {
let $plant := doc($catalog)//PLANT 
for $light in distinct-values($plant/LIGHT) order by $light
return ('&#xa;',<LIGHT> 
    <EXPOSURE>{data($light)}</EXPOSURE>
   {local:plant-by-light($catalog,$light)}
    </LIGHT> )
   } 
</CATALOG>