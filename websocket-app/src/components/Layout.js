import Head from 'next/head';
import Navbar from './Navbar';

function Layout({ children }) {
  return (
    <>
      <Head>
        <title>My App</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <div className="min-h-screen flex flex-col">
        <Navbar />
        <main className="flex-grow ">{children}</main>
        <footer className="bg-gray-200 text-gray-600 py-4 text-center mt-auto">
          &copy; 2023 My App
        </footer>
      </div>
    </>
  );
}

export default Layout;
