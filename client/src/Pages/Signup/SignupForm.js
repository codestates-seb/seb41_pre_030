import { useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const Container = styled.div`
  background-color: white;
  padding: 24px;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
  border-radius: 7px;
  margin-bottom: 16px;
  max-width: 316px;
`
const Form = styled.div`
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
`
const Button = styled.button`
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;

    display: flex;
    align-items: center;
    justify-content: center;
    height: 36px;
    margin-top: 8px;
    width: 100%;

    border: 1px solid hsl(205deg 41% 63%);
    border-radius: 4px;
    font-weight: 500;

    :hover {
      background: var(--button-hover-bg-color);
    }
    :active {
      background: var(--button-active-bg-color);
    }
    cursor: pointer;

    svg {
      margin: 0 5px;
    }
`;

const LoginButton = styled(Button)`
    background-color: hsl(206deg 100% 52%);
    color: #fff;
    --button-hover-bg-color: hsl(209deg 100% 38%);
    --button-active-bg-color: hsl(209deg 100% 32%);
`;

const SignupForm = () => {
  const [info, setInfo] = useState({
    email: '',
    password: '',
    nickName: '',
    confirmedPassword: ''
  })
  const navigate = useNavigate();


  const onSignUpSubmitHandler = async () => {
    const jsonData = JSON.stringify(info);
    console.log(jsonData)

    await axios.post("http://13.125.30.88:8080/members/signup", jsonData,
    {
      headers: {
        "Content-Type": `application/json`
      }
    })
      .then((res) => {
        navigate("/login");
        window.location.reload();
      })
      .catch((err) => {
        console.log(err)
        alert('회원가입에 실패했습니다.');
      })
  }

  const handleKeypress = e => {
    if (e.keyCode === 13) {
      onSignUpSubmitHandler();
    }
  };

  return (
    <Container>
      <Form onKeyUp={e => handleKeypress(e)}>
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
            password: event.target.value,
            confirmedPassword: event.target.value
          })}/>
          <p>Passwords must contain at least eight characters, including at least 1 letter and 1 number.</p>
        </div>
        <LoginButton onClick={onSignUpSubmitHandler}>SignUp</LoginButton>
      </Form>
  </Container>
  )
}

export default SignupForm;