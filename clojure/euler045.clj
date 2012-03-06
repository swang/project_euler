;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Euler #45
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;
; Triangle, pentagonal, and hexagonal numbers are generated by the following formulae:
;
; Triangle	 	T(n)=n(n+1)/2	 	1, 3, 6, 10, 15, ...
; Pentagonal 	P(n)=n(3n-1)/2	 	1, 5, 12, 22, 35, ...
; Hexagonal	 	H(n)=n(2n-1)	 	1, 6, 15, 28, 45, ...
;
; It can be verified that T(285) = P(165) = H(143) = 40755.
;
; Find the next triangle number that is also pentagonal and hexagonal.
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;
; 1533776805
;

(defn triangle-numbers
    ([] (triangle-numbers 1))
    ([n] (lazy-seq (cons (/ (* n (+ n 1)) 2) (triangle-numbers (inc n))))))

(defn pentagonal-numbers
    ([] (pentagonal-numbers 1))
    ([n] (lazy-seq (cons (/ (* n (- (* 3 n) 1)) 2) (pentagonal-numbers (inc n))))))

(defn hexagonal-numbers
    ([] (hexagonal-numbers 1))
    ([n] (lazy-seq (cons (* n (- (* 2 n) 1)) (hexagonal-numbers (inc n))))))
(defn is-int [a]
    (= (Math/floor a) a))

(defn quadratic-formula [a, b, c]
    ^{
        :doc "returns the quadratic formula with coefficients a, b, c"
        ;:test ()
    }
 
    ;-b+Math.sqrt( b^2 - 4ac)
    ;------------------------
    ;          2a
    [( /
        (+ 
            (- b)
            (Math/sqrt
                (- 
                    (* b b)
                    (* 4 a c)
                )
            )
        )
        (* 2 a)
    )
    
    ( /
        (- 
            (- b)
            (Math/sqrt
                (- 
                    (* b b)
                    (* 4 a c)
                )
            )
        )
        (* 2 a)
    )]
)
(defn is-pentagonal [n]
    (is-int 
        (first 
            (filter #(< 0 %1)
                (quadratic-formula (float (/ 3 2)) (float (/ -1 2)) (- n)) ))))
                
(defn is-hexagonal [n]
    (->>    (quadratic-formula 2 -1 (- n))
            (filter #(< 0 %))
            first
            is-int))
(comment 
    ; this was my original loop, not sure which one is faster, run benchmark later.
    (println (first
        (for [
            t (take 100000 (triangle-numbers 1))
                :when (and (is-pentagonal t) (is-hexagonal t))
            ]
            t)))
)
(println 
    (loop [x 286] ;start at 286 since triangle-numbers @285 returns 40755
        (if
            (let [t (first (triangle-numbers x))]
                (and (is-pentagonal t) (is-hexagonal t))
            )
            (first (triangle-numbers x))
            (recur (+ x 1)))))