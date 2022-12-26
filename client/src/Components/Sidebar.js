import styled from "styled-components"
import { NavLink } from 'react-router-dom'

const SideAllbar = styled.nav`
  width:200px;
  height: 100vh;
  margin-left: 80px;
  position: fixed;
`

const SideHomeBar = styled.div`
  margin-left: 20px;
  margin-top: 40px;
  font-size: 20px;
  display: grid;
`

const SidePublicBar = styled.div`
  margin-left: 20px;
  margin-top: 30px;
  font-size: 20px;
  display: grid;
  padding-left:10px;
`
// Home or Users 페이지에 들어갈 때 현재 페이지를 나타내는 라벨링 
const ActiveStyle = styled(NavLink)`
  margin-top: 15px;
  text-decoration: none;
  color: inherit;
  padding-left:10px;
  height: 30px;
  &.active {
    border-right: 4px solid orange;
    background-color: whitesmoke;
  }
`

const Sidebar = () => {

  return (
    <SideAllbar>   
      <SideHomeBar>
        <ActiveStyle to='/'>HOME</ActiveStyle>
      </SideHomeBar>
      <SidePublicBar>
        PUBLIC
        <ActiveStyle to='/members' style={{paddingLeft:"40px"}}>Users</ActiveStyle>
      </SidePublicBar>
    </SideAllbar>  
  )
}

export default Sidebar;