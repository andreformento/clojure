(ns pegthing.util)

;[board max-pos pos neighbor destination]
(def board {})
(def max-pos 15)
(def pos 1)
(def neighbor 2)
(def destination 4)

(reduce (fn [new-board [p1 p2]]
          (assoc-in new-board [p1 :connections p2] neighbor))
        board
        [[pos destination] [destination pos]])
; {4 {:connections {1 2}},
;  1 {:connections {4 2}}}
