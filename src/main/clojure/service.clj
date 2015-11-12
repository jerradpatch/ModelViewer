(ns service
  (:import org.springframework.stereotype.Controller
            (org.springframework.web.bind.annotation RequestMapping RequestMethod ResponseBody))
  (:gen-class
    :name ^{Controller "TEST"} com.ModelViewer.LoginApp.Service.Clojure
    :methods [[^{RequestMapping {:value ["/test"] :method [RequestMethod/GET]} ResponseBody {}} hello [] String]]))

(defn -hello [this]
  "Hello world from a Clojure-based controller!")