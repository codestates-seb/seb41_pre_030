import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Nav = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  margin: 16px;
`;

const Button = styled.button`
  border: none;
  border-radius: 8px;
  padding: 8px;
  margin: 0;
  background: black;
  color: white;
  font-size: 1rem;

  &:hover {
    background: tomato;
    cursor: pointer;
    transform: translateY(-2px);
  }

  &[disabled] {
    background: grey;
    cursor: revert;
    transform: revert;
  }

  &[aria-current] {
    background: deeppink;
    font-weight: bold;
    cursor: revert;
    transform: revert;
  }
`;

const Pagenation = ({ total, limit, page, setPage}) => {
    const numPages = Math.ceil(total / limit);
    const reloadHelper = (i) => {
        setPage(i);
        localStorage.setItem("searchPage", i);
        window.location.reload();
    }
    return (
        <>
            <Nav>
                <Button onClick={() => setPage(page - 1)} disabled={page === 1}>
                    &lt;
                </Button>
                {Array(numPages)
                    .fill()
                    .map((_, i) => (
                        <Link to={`/?page=${i+1}`} key={i + 1}>
                            <Button
                                
                                onClick={() => reloadHelper(i + 1)}
                                aria-current={page === i + 1 || localStorage.getItem("searchPage") == i + 1 ? "page" : null}
                            >
                                {i + 1}
                            </Button>
                        </Link>
                    ))}
                <Button onClick={() => setPage(page + 1)} disabled={page === numPages}>
                    &gt;
                </Button>
            </Nav>
        </>
    );
}

export default Pagenation;