import { useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const Container = styled.div`
  background-color: white;
  padding: 24px;
  box-shadow: rgba(0, 0, 0, 0.5) 0px 10px 24px 0;
  border-radius: 7px;
  margin-bottom: 16px;
  max-width: 316px;
`
const Form = styled.form`
  display: flex;
  flex-direction: column;
  margin-top: -6px;
  div.input-box{
    display: flex;
    flex-direction: column;
    margin: 6px 0 6px 0;
    label {
      font-size: 1.15rem;
      font-weight: 600;
      margin: 2px 0 2px 0;
      padding: 0 2px 0 2px;
    }
    input {
      width: 100%;
      padding: 0.6em 0.7em;
      border: rgb(186, 191, 196) solid 1px;
      border-radius: 3px;
    }
    p {
      margin: 4px 0 140px 0;
      font-size: 12px;
      color: rgb(106, 115, 124);
    }
  }
  button {
    background: rgb(10, 149, 255);
    border: none;
    border-radius: 3px;
    padding: 10px;
    color: white;
    cursor: pointer;
  }
`

const SignupForm = () => {
  const [info, setInfo] = useState({
    email: '',
    password: '',
    nickName: '',
    confirmedPassword: ''
  })
  const navigate = useNavigate();


  const onSignUpSubmitHandler = (event) => {
    event.preventDefault();
    
    const jsonData = JSON.stringify(info);

    axios.post("http://13.125.30.88:8080/members/signup", jsonData)
      .then((res) => {
        navigate("/login");
        // 현재 로그인 요청 보내면 로그인페이지 -> 회원가입 페이지로 되돌아옴
        // url에 인풋 데이터는 남아있는 상태로 돌아옴
      })
      .catch((err) => {
        console.log(err)
        alert('회원가입에 실패했습니다.');
      })
  }

  return (
    <Container>
      <Form onSubmit={event => onSignUpSubmitHandler(event)}>
        <div className='input-box'>
          <label htmlFor='display-name'>Display name</label>
          <input type='text' 
          id='display-name' 
          name='display-name' 
          onChange={event => setInfo({
            ...info,
            nickName: event.target.value
          })}/>
        </div>
        <div className='input-box'>
          <label htmlFor='email'>Email</label>
          <input type='text' 
          id='email' 
          name='email' 
          onChange={event => setInfo({
            ...info,
            email: event.target.value
          })}/>
        </div>
        <div className='input-box'>
          <label htmlFor='password'>Password</label>
          <input type='text' 
          id='password' 
          name='password'
          onChange={event => setInfo({
            ...info,
            password: event.target.value
          })}/>
          <p>Passwords must contain at least eight characters, including at least 1 letter and 1 number.</p>
        </div>
        <button>SignUp</button>
      </Form>
  </Container>
  )
}

export default SignupForm;