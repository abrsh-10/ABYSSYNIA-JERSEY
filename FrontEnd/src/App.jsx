import Navbar from "./components/navbar";
import {createBrowserRouter,Outlet} from "react-router-dom";
import Landing from "./pages/landing/components";
import PageNotFound from "./components/PageNotFound";

const AppLayout = () => {

    return(
      <>
          <Navbar/>
          <Outlet />
            
      </>
    )
  }

  const router = createBrowserRouter([
      {
        path: "",
        element: <Landing/>,
      },
      {
        path: "*",
        element: <PageNotFound />
      }
    ]);
  
  export {router,AppLayout};
