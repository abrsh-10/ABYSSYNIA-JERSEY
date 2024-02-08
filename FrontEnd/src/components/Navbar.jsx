import {cart,hamburger,search,heart,person} from '../assets/icons/index'
import logo from '../assets/images/logo.png'
import Sidebar from 'react-sidebar';
import { useState } from 'react';
const Navbar = () => {

const [sidebarOpen, setSidebarOpen] = useState(false);
const [leaguesShown,setLeaguesShown] = useState(false);
const [womenShown,setWomenShown] = useState(false);
const [accountShown,setAccountShown] = useState(false);


  const onSetSidebarOpen = (open) => {
    setSidebarOpen(open);
  };


  return (
    <header>
        <Sidebar
        sidebar=
        {
        <div className='p-4'>
        <div className='mr-5 mt-3 text-end'>
            <i className="fa fa-times text-white"></i>
        </div>
        <ul className='flex flex-col gap-5 w-full mt-6'>
            <li><a href="/" className='text-white '>Home</a></li>
            <li><a href="/" className='text-white '>Best Sellers</a></li>
            <li className='flex justify-between' onClick={() => setLeaguesShown(prev => !prev)}>
                <a className='text-white cursor-pointer '>Leagues</a>
                {leaguesShown?<i className="fa fa-angle-up ml-2 text-white"></i>:<i className="fa fa-angle-down ml-2 text-white"></i>}
            </li>
            <div className={`${leaguesShown?'opacity-100 block':'opacity-0 hidden'} `}>
                 <ul>
                     <li className= '    text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>Premier League</li>
                     <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>Laliga</li>
                     <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>BundesLiga</li>
                     <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>Serie A</li>
                     <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>Ligue 1</li>
                     <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>Other</li>
                 </ul>
            </div>
            <li><a href="/" className='text-white '>Kids</a></li>
            <li><a href="/" className='text-white '>International</a></li>
            <li className='flex justify-between' onClick={() => setWomenShown(prev => !prev)}>
                <a className='text-white cursor-pointer'>Women</a>
                {womenShown?<i className="fa fa-angle-up ml-2 text-white"></i>:<i className="fa fa-angle-down ml-2 text-white"></i>}
            </li>
            <div className={`${womenShown?'opacity-100 block':'opacity-0 hidden'} `}>
                 <ul>
                     <li className= '    text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>Club</li>
                     <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>International</li>
                 </ul>
            </div>
            <li><a href="/" className='text-white '>Pre-Match</a></li>
            <li className='flex justify-between' onClick={() => setAccountShown(prev => !prev)}>
                <a className='text-white cursor-pointer'>My Account</a>
                {accountShown?<i className="fa fa-angle-up ml-2 text-white"></i>:<i className="fa fa-angle-down ml-2 text-white"></i>}
            </li>
            <div className={`${accountShown?'opacity-100 block':'opacity-0 hidden'} `}>
                 <ul>
                     <li className= '    text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>Login</li>
                     <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>My Cart</li>
                    <li className='my-2 text-white pl-4 pr-10 text-sm cursor-pointer h-7 flex items-center text-start'>My Wishlist</li>

                 </ul>
            </div>

s
        </ul>
        </div>
        }
        open={sidebarOpen}
        onSetOpen={onSetSidebarOpen}
        docked={false}
        styles={{ sidebar: { background: '#000000', width: '280px' } }}>
      </Sidebar>
    <div className="w-full flex border-2 justify-between items-center px-5 xl:hidden">
        <div className=' relative'>
            <img src={hamburger} alt="menu" className=' w-6' onClick={() => onSetSidebarOpen(true)}/>
        </div>
        <div className=' relative'>
            <a href="/"><img src={logo} alt="logo" className=' w-40'/></a>
        </div>
        <div className=' relative'>
            <a href="/cart"><img src={cart} alt="cart" className=' w-7 mx-3 my-2'/></a>
            <div className='rounded-full w-5 h-5 absolute top-0 right-0 bg-coral-red text-white flex justify-center items-center'>
                3
            </div>
        </div>
    </div>

    <div className='w-full flex gap-8 pl-0 max-xl:hidden border-2 my-1'>
       <div className=' relative border-x-2 p-3'>
            <a href="/"><img src={logo} alt="logo" className=' w-40'/></a>
        </div>
        <div className="flex flex-col gap-y-5 relative w-full justify-center">
            <div className="flex justify-between gap-5 px-4 relative ">
                <div className=''>
                <input type="text" name="search" id="search"
                 className='w-[36rem] px-10 py-5 relative rounded-sm h-8 border items-start' placeholder='Search For Everything'/>
                <div className="absolute w-6 h-6 top-2.5 left-5">
                    <img src={search} alt="" className="filter grayscale" />
                </div>
                </div>
           <div className='flex'>
                <div className='relative group'>
                <a href="/"><img src={person} alt="logo" className=' w-7 mx-3 my-2'/></a>
                <div className="p-5 hidden absolute group-hover:flex flex-col right-10 w-80 transition-all duration-300 border border-t-orange-500 border-t-2 bg-white z-10 shadow-lg">
                        <div className="flex justify-between">
                            <div className='text-lg mb-3'>
                                Sign In
                            </div>
                            <div className="text-red-400 hover:underline mb-3 cursor-pointer">
                                Create an account
                            </div>
                        </div>
                        <div className="text-start">
                            Username or email <span className='text-red-400'>*</span>
                        </div>
                        <input type="text" name="username" id="username" className='w-64 px-2 py-5 text relative rounded-sm h-8 border items-start mb-3' placeholder='Username'/>
                        <div className="text-start">
                            Password <span className='text-red-400'>*</span>
                        </div>
                         <input type="password" name="password" id="password" className='w-64 px-2 py-5 mb-3 relative rounded-sm h-8 border items-start' placeholder='Password'/>
                        <button className='w-64 py-3 bg-red-500 hover:bg-green-500 mx-auto my-3 rounded-md text-white'>Login</button>
                        <div className="text-start text-red-400 hover:underline cursor-pointer">
                            Lost your password?
                        </div>
                       </div>
            </div>
            <div className='relative'>
               <a href="/"><img src={heart} alt="logo" className=' w-7 mx-3 my-2'/></a>
               <div className='rounded-full w-5 h-5 absolute top-0 left-8 bg-coral-red text-white flex justify-center items-center'>
                3
            </div>
               
            </div>
        <div className='relative'>
            <a href="/"><img src={cart} alt="logo" className=' w-7 mx-3 my-2'/></a>
            <div className='rounded-full w-5 h-5 absolute top-0 left-8 bg-coral-red text-white flex justify-center items-center'>
                3
            </div>
        </div>
        <div className="my-3 ml-2 font-bold">
            $0.00
        </div>
        </div>
            </div>
            <div className='flex gap-14'>
                <div className="font-bold hover:text-green-400 cursor-pointer">Best Sellers</div>
                <div className="group">
                       <div className="font-bold hover:text-green-400 cursor-pointer pb-5">
                         Leagues
                          <i className="fa fa-angle-down ml-2"></i>
                       </div>
                       <div className="hidden absolute group-hover:block transition-all duration-300 border bg-white z-10 shadow-lg">
                        <ul>
                            <li className='mt-5 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>Premier League</li>
                            <li className='my-2 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>Laliga</li>
                            <li className='my-2 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>BundesLiga</li>
                            <li className='my-2 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>Serie A</li>
                            <li className='my-2 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>Ligue 1</li>
                            <li className='my-2 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>Other</li>
                        </ul>
                       </div>
                </div>
                <div className="font-bold hover:text-green-400 cursor-pointer">Kids</div>
                <div className="font-bold hover:text-green-400 cursor-pointer">International</div>
                <div className="group">
                       <div className="font-bold hover:text-green-400 cursor-pointer pb-5">
                         Women
                         <i className="fa fa-angle-down ml-2"></i>
                       </div>
                       <div className="hidden absolute group-hover:block transition-all duration-300 border bg-white z-10 shadow-lg">
                        <ul>
                            <li className='my-2 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>Club</li>
                            <li className='my-2 text-slate-400 hover:text-red-400 hover:bg-slate-300 pl-4 pr-10 cursor-pointer h-10 flex items-center text-start'>International</li>
                        </ul>
                       </div>
                </div>
                <div className="font-bold hover:text-green-400 cursor-pointer">Pre-Match</div>

            </div>

        </div>
    </div>
    </header>
  )
}

export default Navbar;