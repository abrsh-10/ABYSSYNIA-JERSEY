import ReactDOM from 'react-dom/client'
import {AppLayout,router} from './App.jsx'
import './index.css'
import { BrowserRouter as Router, RouterProvider } from "react-router-dom";

ReactDOM.createRoot(document.getElementById('root')).render(
   <>
        <RouterProvider router={router}>
            <Router>
                <AppLayout />
            </Router>
        </RouterProvider>
    </>
)
