(ns utils)

(defmacro nmap [& xs]
  (cons 'hash-map
        (interleave (map (comp keyword name) xs) xs)))

(comment
  (let [a 1 b 2 c 3]
    (nmap a b c))

  (let [a 1 b 2 c 3]
    (macroexpand
     (nmap a b c))))

(defmacro nnames [& xs]
  (set (mapv (comp keyword name) xs)))

(comment
  (let [a 1 b 2 c 3]
    (nnames a b c))

  (let [a 1 b 2 c 3]
    (=
     (nnames a b c)
     (set (keys (nmap a b c))))))


