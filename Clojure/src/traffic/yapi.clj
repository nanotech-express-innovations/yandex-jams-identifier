(ns traffic.yapi
	 (:require 
	 	[clojure.data.json :as json]
	 	[clojure.string :as string]))

(defn input [streetname]
	(slurp (apply str "http://geocode-maps.yandex.ru/1.x/?format=json&geocode=Москва,+" streetname)))

(defn get-street-json [streetname]
	(json/read-str (input streetname)
		:key-fn keyword))

(defn reverse-recursively [coll]
  (loop [[r & more :as all] (seq coll)
         acc '()]
    (if all
      (recur more (cons r acc))
      acc)))

(defn get-street-lower [streetname]
	(def buf (take 1 ( map :lowerCorner (map :Envelope (map :boundedBy 
		(map :GeoObject (:featureMember 
			(:GeoObjectCollection 
				(:response (get-street-json streetname))))))))))
	(reverse 
		(string/split 
			(apply str buf) #"\s")))

(defn get-street-upper [streetname]
	(def buf (take 1 (map :upperCorner (map :Envelope (map :boundedBy 
		(map :GeoObject (:featureMember 
			(:GeoObjectCollection 
				(:response (get-street-json streetname))))))))))
	(reverse 
		(string/split 
			(apply str buf) #"\s")))