declare function local:family-of-plant ($families,$plantName) { 
   for $family in (doc($families)//FAMILY)
        where some $specie in $family/SPECIES 
        satisfies ($specie = data($plantName)) 
        return (<FAMILY>{data($family/NAME)}</FAMILY>)
}; 
let $catalog := "exercice-3/plant_catalog.xml"
let $families := "exercice-3/plant_families.xml"

return
('&#xa;',<PLANTS>
	{ 
		for $plant in (doc($catalog)//PLANT)
      return (
      '&#xa;',<PLANT> 
      { $plant/child::* }    
      {local:family-of-plant($families,$plant/BOTANICAL)}
      </PLANT>,'&#xa;')
      
	}
</PLANTS>,'&#xa;')