import { useState } from "react";
import styled from "styled-components";
import Logo from "../Image/Logo";
import { Link } from "react-router-dom";

const StyledHeader = styled.header`
  background-color: hsl(210deg 8% 98%);
  box-shadow: 0px 3px 3px rgba(0, 0, 0, 0.2);
  display: grid;
  grid-template-columns: 210px 1fr 70px 100px;
  grid-column-gap: 20px;
  position: sticky;
  left: 0;
  top: 0;
  z-index: 20;
`;

const LogoLink = styled(Link)`
  color: rgba(0, 0, 0);
  text-decoration: none;
  display: inline-block;
  height: 50px;
  line-height: 30px;
  padding: 0px 15px;

  :hover {
    background-color: rgba(0, 0, 0, 0.1);
  }

  svg {
    margin-top: 8px;
    float: left;
  }

  span {
    display: inline-block;
    padding-left: 5px;
    padding-top: 13px;
    font-size: 1.2rem;
    font-weight: 350;
  }

  b {
    font-weight: bold;
    margin-left: 2px;
  }
`;

const SearchInput = styled.input`
  display: inline-block;
  width: 100%;
  border: 1px solid #999;
  border-radius: 3px;
  padding: 10px 10px;
  margin-top: 7px;
`;

const LoginLink = styled(Link)`
  button {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;

    display: inline-block;
    height: 36px;
    padding: 0px 15px;
    margin-top: 8px;
    width: auto;

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
  }

  .login {
    background-color: hsl(205deg 46% 92%);
    color: hsl(205deg 47% 42%);
    --button-hover-bg-color: hsl(205deg 57% 81%);
    --button-active-bg-color: hsl(205deg 56% 76%);
  }
`;

const SignupLink = styled(Link)`
  button {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;

    display: inline-block;
    height: 36px;
    padding: 0px 15px;
    margin-top: 8px;
    width: auto;

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
  }

  .signUp {
    background-color: hsl(206deg 100% 52%);
    color: #fff;
    --button-hover-bg-color: hsl(209deg 100% 38%);
    --button-active-bg-color: hsl(209deg 100% 32%);
  }
`;

const Header = () => {
  const [search, setSearch] = useState("");

  const onChange = (e) => {
    setSearch(e.target.value);
    console.log(search)
  };

  return (
    <StyledHeader>
      <LogoLink to={'/'} className="logo">
        <Logo />
        <span>
          stack<b>overclone</b>
        </span>
      </LogoLink>
      <form action="http://www.naver.com" className="search">
        <SearchInput
          type="text"
          placeholder="Search..."
          value={search}
          onChange={onChange}
        />
      </form>
      <LoginLink to={"/login"}>
        <button className="login">Log in</button>
      </LoginLink>
      <SignupLink to={"/signup"}>
        <button className="signUp">Sign up</button>
      </SignupLink>
    </StyledHeader>
  );
};
export default Header;
