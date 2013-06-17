(ns traffic.core
  (:gen-class)
  (:use [traffic.yapi]))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (println (get-street-upper "Тверская"))
  (println (get-street-lower "Тверская")))
