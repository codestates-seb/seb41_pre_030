import styled from "styled-components";
import Logo from "../Image/Logo";
import GoogleLogo from "../Image/GoogleLogo";
import GitLogo from "../Image/GitLogo";
import { Component } from "react";
import { Link } from "react-router-dom";
import axios from 'axios';



const Container = styled.div`
  width: 268px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate3d(-50%, -50%, 0);

  display: grid;
  grid-template-rows: repeat(auto-fill, minmax(20%, auto));

  border-radius: 10px;
  background: rgba(0, 0, 0, 0);
`;

const UpContainer = styled.div`
  padding: 20px 0;
  svg {
    display: block;
    margin: auto;
    margin-bottom: 20px;
  }
`;

const RowContainer = styled.div`
  padding: 30px 20px;
  border-radius: 10px;
  background: #ffffff;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
`;

const LoginHeader = styled.h2`
  font-size: 1.2rem;
`;

const LoginInput = styled.input`
  background: none;
  border: 1px solid #777;
  border-radius: 3px;
  display: block;
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  color: black;
`;

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

const GoogleButton = styled(Button)`
    background-color: hsl(0deg 0% 100%);
    color: hsl(210deg 8% 20%);
    border-color: hsl(210deg 8% 85%);
    --button-hover-bg-color: hsl(210deg 8% 98%);
    --button-active-bg-color: hsl(210deg 8% 95%);
`;

const GitButton = styled(Button)`
    background-color: hsl(210deg 8% 20%);
    color: #fff;
    border-color: hsl(210deg 8% 85%);
    --button-hover-bg-color: hsl(210deg 8% 15%);
    --button-active-bg-color: hsl(210deg 8% 5%);
`;

class LoginPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
    }
  }
  
  login() {
    axios.post({url:''}, {data: {
      email: this.state.email,
      password: this.state.password,
    }})
  }

  render() {
    return (
      <Container>
        <UpContainer>
          <Link to={'/'} className="logo">
            <Logo className="stackLogo"/>
          </Link>
          <GoogleButton>
            <GoogleLogo />Log in with Google
          </GoogleButton>
          <GitButton>
            <GitLogo />Log in with GitHub
          </GitButton>
        </UpContainer>
        <RowContainer>
          <LoginHeader>Email</LoginHeader>
          <LoginInput type="email" value={this.state.email}
                      onChange={ev => this.setState({email:ev.target.value})}/>
          <LoginHeader>Password</LoginHeader>
          <LoginInput type="password" value={this.state.password}
                      onChange={ev => this.setState({password:ev.target.value})}/>
          <LoginButton className="login" onClick={() => this.login()}>Log in</LoginButton>
        </RowContainer>
      </Container>
    )
  }
};

export default LoginPage;