declare function local:price-of-plant ($catalog,$plantName) { 
   for $plant in (doc($catalog)//PLANT)
        where some $name in $plant/COMMON 
        satisfies ($name = data($plantName)) 
        return (number(substring-after(data($plant/PRICE),'$')))
}; 

declare function local:all-prices ($order,$catalog) { 
   for $command in (doc($order)//PLANT)
      return (
        data($command/QUANTITY)*local:price-of-plant($catalog,$command/COMMON)
      )
}; 
let $catalog := "exercice-3/plant_catalog.xml"
let $order := "exercice-3/plant_order.xml"

return
('&#xa;',<PRICE>
  { 
    sum(local:all-prices($order,$catalog))
  }
</PRICE>,'&#xa;')