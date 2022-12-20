import React, { useState } from "react"
import styled from "styled-components"
import { Link } from 'react-router-dom'

const SideAllbar = styled.div`
  width:200px;
  height: 100vh;
  margin-left: 164px;
  margin-top: 50px;
  border-right: 1px solid black;
  position: fixed;
`

const SideHomeBar = styled.div`
  margin-left: 20px;
  font-size: 20px;
`

const SidePublicBar = styled.div`
  margin-left: 20px;
  margin-top: 20px;
  font-size: 20px;
  display: grid;
`
// Home이나 Users페이지에 들어갔을 때 각각의 페이지 상태를 나타내 주는 표시를 아래 style 적용하면 될 듯
const UsersClicked = styled(Link)`
  background-color: whitesmoke;
  height: 30px;
  width: 180px;
  margin-top: 15px;
  font-size: 20px;
  border: 0ch;
  border-right: 4px solid orange;
  text-align: left;
  text-decoration: none;
`;

const Sidebar = () => {

  return (
    <SideAllbar>
      <SideHomeBar>
        <Link to='/' style={{textDecoration: "none"}}>HOME</Link>
      </SideHomeBar>
      <SidePublicBar>
        PUBLIC
          <UsersClicked to='/users' >&nbsp;&nbsp;&nbsp;&nbsp;Users</UsersClicked>
      </SidePublicBar>
    </SideAllbar>  
  )
}

export default Sidebar;