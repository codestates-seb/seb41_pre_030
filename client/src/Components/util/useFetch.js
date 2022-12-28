import { useState, useEffect } from "react";

const useFetch = (url) => {
  const [question, setQuestion] = useState("")

  useEffect (()=> {
    setTimeout(()=> {
      fetch(url)
      .then(res => {
        if(!res.ok) {
          throw Error("Error!")
        }
        return res.json()
      })
      .then(data => {
        setQuestion(data)
        console.log(data)
      })
      .catch(err => {
        console.log("err")
      })
    })
  },[])
  return [question,setQuestion]
}

export default useFetch