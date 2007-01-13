jmaki.namespace("jmaki.widgets.scriptaculous.puzzle");

jmaki.widgets.scriptaculous.puzzle.Widget = function(wargs) {
   this.wrapper = Sortable.create(wargs.uuid,
     {tag:'img',overlap:'horizontal',constraint: false,
      onUpdate:function(){
        p = $(wargs.uuid);
        p.moves = p.moves || 0;
        p.moves++;
        $(wargs.uuid + '_puzzleinfo').innerHTML = 
          "You've made " + p.moves + " move" + 
          (p.moves>1 ? "s" : "");
        if(Sortable.serialize(wargs.uuid)==
           "puzzle[]=1&puzzle[]=2&puzzle[]=3&" +
           "puzzle[]=4&puzzle[]=5&puzzle[]=6&" +
           "puzzle[]=7&puzzle[]=8&puzzle[]=9") {
           $(wargs.uuid + '_puzzleinfo').innerHTML = 
             "You've solved the puzzle in <i>" + p.moves + "</i> moves!";
           Element.Class.add(wargs.uuid + '_puzzleinfo','congrats');
        }
      }
    });
}