declare function local:plant-by-light($catalog,$searchLight,$families) { 
   for $plant in (doc($catalog)//PLANT)
        where some $light in $plant/LIGHT 
        satisfies ($light = data($searchLight)) 
        return local:plant-with-family($catalog,$families)
};
declare function local:family-of-plant ($families,$plantName) { 
   for $family in (doc($families)//FAMILY)
        where some $specie in $family/SPECIES 
        satisfies ($specie = data($plantName)) 
        return (<FAMILY>{data($family/NAME)}</FAMILY>)
}; 
declare function local:plant-with-family ($catalog,$families) { 
   for $plant in (doc($catalog)//PLANT)
   		order by $plant/COMMON
      	return (
	    '&#xa;',<PLANT> 
	    { $plant/child::node() except $plant/LIGHT }    
	    {local:family-of-plant($families,$plant/BOTANICAL)}
	    </PLANT>,'&#xa;')
}; 
let $catalog := "exercice-3/plant_catalog.xml"
let $families := "exercice-3/plant_families.xml"
return
<CATALOG> {
let $plant := doc($catalog)//PLANT 
for $light in distinct-values($plant/LIGHT) order by $light
return ('&#xa;',<LIGHT> 
    <EXPOSURE>{data($light)}</EXPOSURE>
   {local:plant-by-light($catalog,$light,$families)}
    </LIGHT> )
   } 
</CATALOG>