declare namespace ns = "http://www.expression.org";
declare option saxon:output "omit-xml-declaration=yes";

declare function local:print($nodes) {  
  if(local-name($nodes)="op") 
  then concat("(",local:print($nodes/*[1]),
              $nodes/@val,
              local:print($nodes/*[2]),")"
              )
  else $nodes/text()
};

declare function local:eval($nodes) as xs:integer {
  
  if(local-name($nodes)="op") 
  then 
    let $child-right := local:eval($nodes/*[2])
    let $child-left := local:eval($nodes/*[1])
      return 
        if($nodes/@val = "+")
        then $child-left + $child-right
        else if($nodes/@val = "-")
        then $child-left - $child-right
        else if($nodes/@val = "/")
        then xs:integer($child-left div $child-right)
        else if($nodes/@val = "*")
        then $child-left * $child-right
        else error((),'BREAK')

  else if(local-name($nodes)="var") 
  then error((),'BREAK')
  else if(local-name($nodes)="cons") 
  then xs:integer($nodes/text())
  else error((),'BREAK')
};

declare function local:eval-var($nodes,$variables) {
  if(local-name($nodes)="op") 
  then  
    let $child-right := local:eval-var($nodes/*[2],$variables)
    let $child-left := local:eval-var($nodes/*[1],$variables)
      return
        if($nodes/@val = "+")
        then $child-left + $child-right
        else if($nodes/@val = "-")
        then $child-left - $child-right
        else if($nodes/@val = "/")
        then xs:integer($child-left div $child-right)
        else if($nodes/@val = "*")
        then $child-left * $child-right
        else error((),'BREAK')

  else if(local-name($nodes)="var") 
  then $variables/child::*[name()=$nodes/text()]/text()
  else if(local-name($nodes)="cons") 
  then xs:integer($nodes/text())
  else error((),'BREAK')
};

declare function local:simplifie($nodes,$variables) {
  if(local-name($nodes)="op") 
    then 
      let $child-right := local:simplifie($nodes/*[2],$variables)
      let $child-left := local:simplifie($nodes/*[1],$variables)
        return 
        if($child-left instance of xs:string)
          then concat("(",$child-left,$nodes/@val,$child-right,")")
        else if($child-right instance of xs:string)
          then concat("(",$child-left,$nodes/@val,$child-right,")")
        else
          if($nodes/@val = "+")
          then $child-left + $child-right
          else if($nodes/@val = "-")
          then $child-left - $child-right
          else if($nodes/@val = "/")
          then xs:integer($child-left div $child-right)
          else if($nodes/@val = "*")
          then $child-left * $child-right
          else error((),concat("Unknow operande",$nodes/@val))
            
  else if(local-name($nodes)="var") 
  then 
      if($variables/child::*[name()=$nodes/text()]/text())
      then $variables/child::*[name()=$nodes/text()]/text()
      else xs:string($nodes/text())
  else if(local-name($nodes)="cons") 
  then xs:integer($nodes/text())
  else error((),'BREAK')
};


let $expr := doc("expression4.xml")//ns:expr
let $variables := doc("variables4.xml")//variables

return 
  local:simplifie($expr/*[1],$variables)
