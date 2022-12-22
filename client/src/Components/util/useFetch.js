import { useState, useEffect } from "react";

const useFetch = (url) => {
  const [question, setQuestion] = useState()
  const [answer, setAnswer] = useState()

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
        setAnswer(data)
        console.log(data)
      })
      .catch(err => {
        console.log("err")
      })
    })
  },[url])
  return [question,setQuestion, answer, setAnswer]
}

export default useFetch