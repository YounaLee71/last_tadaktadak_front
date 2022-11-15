package ds.project.tadaktadakfront

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ds.project.tadaktadakfront.contract.*
import ds.project.tadaktadakfront.contract.model.entity.Contract
import ds.project.tadaktadakfront.contract.view.adapter.ContractAdapter
import kotlinx.android.synthetic.main.fragment_navi_contract_collection.*
import kotlinx.android.synthetic.main.fragment_navi_contract_collection.view.*
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.FileOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [NaviContractCollection.newInstance] factory method to
 * create an instance of this fragment.
 */
class NaviContractCollection : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val workBook = HSSFWorkbook()
    private val WRITE_XLS_REQ_CODE = 1000

    var name = ""
    var number = ""
    var address = ""


    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_navi_contract_collection, container, false)

        val btnAllList: AppCompatButton = view.findViewById(R.id.btn_all_list)
        val recyclerView: RecyclerView = view.findViewById(R.id.contact_recyclerview)
        val adapter = ContractAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

        name = requireActivity().intent.getStringExtra("name").toString()
        number = requireActivity().intent.getStringExtra("number").toString()
        address = requireActivity().intent.getStringExtra("address").toString()

        adapter.submitList(arrayListOf(Contract(null, name, number, address)))

        btnAllList.setOnClickListener {
            saveExcel()
        }

        view.add_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val add = getView()?.findViewById<FloatingActionButton>(R.id.add_button)
                add?.setOnClickListener {
                    val intent = Intent(activity, NewContractActivity::class.java)
                    startActivity(intent)
                }
            }
        })
        return view
    }

    private fun saveExcel() {
        val sheet = workBook.createSheet() // 새로운 시트 생성
        var row: HSSFRow
        var cell: HSSFCell

        // 새로운 행 생성
        row = sheet.createRow(0)

        // 1번 셀 생성
        cell = row.createCell(0)
        // 1번 셀 값 입력
        cell.setCellValue("상호명")
        // 2번 셀 생성
        cell = row.createCell(1)
        // 2번 셀 값 입력
        cell.setCellValue("회사번호")
        // 3번 셀 생성
        cell = row.createCell(2)
        // 3번 셀 값 입력
        cell.setCellValue("회사주소")

        // 새로운 행 생성
        row = sheet.createRow(1)

        // 1번 셀 생성
        cell = row.createCell(0)
        cell.setCellValue(name)
        // 2번 셀 생성
        cell = row.createCell(1)
        cell.setCellValue(number)
        // 3번 셀 생성
        cell = row.createCell(2)
        cell.setCellValue(address)

        startActivityForResult(Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/xls"
            putExtra(Intent.EXTRA_TITLE, "근로내역추출.xls")
        }, WRITE_XLS_REQ_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            when (requestCode) {
                WRITE_XLS_REQ_CODE -> {
                    data?.data?.let { uri ->
                        val pfd = requireContext().contentResolver.openFileDescriptor(uri, "w");
                        val os = FileOutputStream(pfd?.fileDescriptor)
                        workBook.write(os)
                        Toast.makeText(requireContext(), "근로내역이 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "근로내역 파일 생성에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onAttach(context: Context) { //메인 context 자유롭게 사용
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NaviContractCollection.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NaviContractCollection().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
