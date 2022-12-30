import { Fragment, useEffect, useState } from "react";
import styled from "styled-components";
import Logo from "../Image/Logo";
import { Link } from "react-router-dom";
import store from '../Redux/store';
import useFetch from './util/useFetch';

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
  margin-top: 5px;
  font-size: 16px
`;

const ButtonLink = styled(Link)` 

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
    font-size: 14px;

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
  
  .signup,.logout {
    background-color: hsl(206deg 100% 52%);
    color: #fff;
    --button-hover-bg-color: hsl(209deg 100% 38%);
    --button-active-bg-color: hsl(209deg 100% 32%);
  }
`;


const ProfileLink = styled(Link)`
  display: inline-block;
  padding: 7px 5px 0px 5px;
  margin: auto;

  :hover {
    background-color: rgba(0, 0, 0, 0.1);
  }

  img {
    width: 35px;
    height: 35px;
  }
`


;

const Header = () => {
  const [questions] = useFetch(`http://13.125.30.88:8080/members/${localStorage.getItem("user")}`);
  const [search, setSearch] = useState("");
  const [state, setState] = useState(store.getState());

  let isLogin = localStorage.getItem("isLogin")
  
  useEffect(() => {
    const unsubscribe = store.subscribe(() => {
      setState(store.getState())
    });
    return () => {
        unsubscribe()
    }
  }, [state])

  const onChange = (e) => {
    setSearch(e.target.value);
    console.log(state)
  };

  const onLogoutHandler = () => {
    localStorage.clear();
    window.location.reload()
  }

  return (
    <StyledHeader>
      <LogoLink to={'/'} className="logo">
        <Logo />
        <span>
          stack<b>overclone</b>
        </span>
      </LogoLink>
      <form action="/search/" method="get">
        <SearchInput 
          type="search"
          placeholder="Search..."
          value={search}
          onChange={onChange}
          name="keyword" 
        />
      </form>
      {isLogin ?
      <Fragment>
        <ProfileLink to={`member/${localStorage.getItem("user")}`} className="profile">
          <img src={questions && questions.data.profileImageSrc}/>
        </ProfileLink>
        <ButtonLink to={"/"}>
          <button className="logout" onClick={onLogoutHandler}>Log out</button>
        </ButtonLink>
      </Fragment> 
        :
      <Fragment>      
        <ButtonLink to={"/login"}>
          <button className="login">Log in</button>
        </ButtonLink>
        <ButtonLink to={"/signup"}>
          <button className="signup">Sign up</button>
        </ButtonLink>
    </Fragment> 
    }

    </StyledHeader>
  );
};
export default Header;
