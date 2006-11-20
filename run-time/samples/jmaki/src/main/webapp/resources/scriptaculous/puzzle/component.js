   Sortable.create(widget.uuid,
     {tag:'img',overlap:'horizontal',constraint: false,
      onUpdate:function(){
        p = $(widget.uuid);
        p.moves = p.moves || 0;
        p.moves++;
        $(widget.uuid + '_puzzleinfo').innerHTML = 
          "You've made " + p.moves + " move" + 
          (p.moves>1 ? "s" : "");
        if(Sortable.serialize("puzzle")==
           "puzzle[]=1&puzzle[]=2&puzzle[]=3&" +
           "puzzle[]=4&puzzle[]=5&puzzle[]=6&" +
           "puzzle[]=7&puzzle[]=8&puzzle[]=9") {
           $(widget.uuid + '_puzzleinfo').innerHTML = 
             "You've solved the puzzle in <i>" + p.moves + "</i> moves!";
           Element.Class.add(widget.uuid + '_puzzleinfo','congrats');
        }
      }
    })
    
    