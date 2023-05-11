import Layout from '../components/Layout';

function Dashboard() {
  return (
    <Layout>
      <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-4">
        {/* Placeholder widget #1 */}
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Widget #1
          </h2>
          <p className="text-gray-600">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
            mattis elit vitae enim pulvinar, sed egestas velit fermentum. Fusce
            volutpat commodo sem, quis bibendum eros interdum sed.
          </p>
        </div>

        {/* Placeholder widget #2 */}
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Widget #2
          </h2>
          <p className="text-gray-600">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
            mattis elit vitae enim pulvinar, sed egestas velit fermentum. Fusce
            volutpat commodo sem, quis bibendum eros interdum sed.
          </p>
        </div>

        {/* Placeholder widget #3 */}
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Widget #3
          </h2>
          <p className="text-gray-600">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
            mattis elit vitae enim pulvinar, sed egestas velit fermentum. Fusce
            volutpat commodo sem, quis bibendum eros interdum sed.
          </p>
        </div>

        {/* Placeholder widget #4 */}
        <div className="bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Widget #4
          </h2>
          <p className="text-gray-600">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
            mattis elit vitae enim pulvinar, sed egestas velit fermentum. Fusce
            volutpat commodo sem, quis bibendum eros interdum sed.
          </p>
        </div>
      </div>
    </Layout>
  );
}

export default Dashboard;
