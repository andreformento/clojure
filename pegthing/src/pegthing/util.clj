(ns pegthing.util)

(def board-5-assert
  {1     {:pegged true, :connections {6 3, 4 2}},
   2     {:pegged true, :connections {9 5, 7 4}},
   3     {:pegged true, :connections {10 6, 8 5}},
   4     {:pegged true, :connections {13 8, 11 7, 6 5, 1 2}},
   5     {:pegged true, :connections {14 9, 12 8}},
   6     {:pegged true, :connections {15 10, 13 9, 4 5, 1 3}},
   7     {:pegged true, :connections {9 8, 2 4}},
   8     {:pegged true, :connections {10 9, 3 5}},
   9     {:pegged true, :connections {7 8, 2 5}},
   10    {:pegged true, :connections {8 9, 3 6}},
   11    {:pegged true, :connections {13 12, 4 7}},
   12    {:pegged true, :connections {14 13, 5 8}},
   13    {:pegged true, :connections {15 14, 11 12, 6 9, 4 8}},
   14    {:pegged true, :connections {12 13, 5 9}},
   15    {:pegged true, :connections {13 14, 6 10}},
   :rows 5})

(def board-5 (new-board 5))

(= board-5-assert board-5)

;[board max-pos pos neighbor destination]
(def empty-board {})
(def max-pos 15)
(def pos 1)
(def neighbor 2)
(def destination 4)

(def board-5-initialized (remove-peg board-5 4))

(defn get-valid-moves-from-position
  [board current-pos]
  (let [valid-movement (valid-moves board current-pos)
        has-valid-moves (not (empty? valid-movement))]
    (if has-valid-moves
      {:from (apply first valid-movement)
       :to   current-pos}
      nil)))

(defn get-all-possible-moves
  [board]
  (if (empty? board)
    (list)
    (let [all-moves (map #(first %) (filter #(not (= :rows (first %))) board))
          validated-moves (map #(get-valid-moves-from-position board %) all-moves)]
      (remove nil? validated-moves))))

(get-all-possible-moves board-5-initialized)
; ({:from 4, :to 1}
;  {:from 4, :to 6}
;  {:from 4, :to 11}
;  {:from 4, :to 13})

; TODO
(def board-5-first-movement (move-peg board-5-initialized 4 1))
board-5-first-movement
;{1 {:connections {6 3, 4 2}, :pegged true},
; 2 {:connections {9 5, 7 4}, :pegged true},
; 3 {:connections {10 6, 8 5}, :pegged true},
; 4 {:pegged false, :connections {13 8, 11 7, 6 5, 1 2}},
; 5 {:connections {14 9, 12 8}, :pegged true},
; 6 {:pegged true, :connections {15 10, 13 9, 4 5, 1 3}},
; 7 {:pegged true, :connections {9 8, 2 4}},
; 8 {:pegged true, :connections {10 9, 3 5}},
; 9 {:pegged true, :connections {7 8, 2 5}},
; 10 {:pegged true, :connections {8 9, 3 6}},
; 11 {:pegged true, :connections {13 12, 4 7}},
; 12 {:pegged true, :connections {14 13, 5 8}},
; 13 {:pegged true, :connections {15 14, 11 12, 6 9, 4 8}},
; 14 {:pegged true, :connections {12 13, 5 9}},
; 15 {:pegged true, :connections {13 14, 6 10}}, :rows 5}


(get-all-possible-moves board-5-first-movement)
;({:current-pos 4, :valid-movement {1 2}}
; {:current-pos 6, :valid-movement {1 3}})

(def board-5-second-movement (move-peg board-5-first-movement 1 4))
(get-all-possible-moves board-5-second-movement)
;({:current-pos 1, :valid-movement {4 2}}
; {:current-pos 6, :valid-movement {4 5}}
; {:current-pos 11, :valid-movement {4 7}}
; {:current-pos 13, :valid-movement {4 8}})

(def board-5-third-movement (move-peg board-5-second-movement 1 4))
(get-all-possible-moves board-5-third-movement)
;({:current-pos 4, :valid-movement {1 2}}
; {:current-pos 6, :valid-movement {1 3}})


(defn make-connections
  [board]
  (reduce (fn [new-board [p1 p2]]
            (assoc-in new-board [p1 :connections p2] neighbor))
          board
          [[pos destination] [destination pos]]))
(make-connections empty-board)
; {4 {:connections {1 2}},
;  1 {:connections {4 2}}}

