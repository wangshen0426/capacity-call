package util;

/*
 * 字符串处理工具
 * 唐瑜璐
 */
public class StringTools
{
	
	// 该函数用于把  getDuty  改为  duty  如果为isDuty则改为duty
	public static String removeGet(String oldStr)
	{
		String strTemp=null;
		String strFirstChar=null;
		String strFinish=null;
		
		if(oldStr.startsWith("get"))
		{
			strTemp=oldStr.substring(3);
		}
		else if(oldStr.startsWith("is"))
		{
			strTemp=oldStr.substring(2);
		}
		else
		{
			strTemp=oldStr;
		}
		
		strFirstChar=strTemp.substring(0, 1);
		strFirstChar=strFirstChar.toLowerCase();
		strFinish=strFirstChar+strTemp.substring(1);
		return strFinish;
	}
	
	//该函数用于把 cn.simple.entity.duty.MyDuty变为 MyDuty
	public static String getShortTypeName(String fullTypeName)
	{
		return fullTypeName.substring(fullTypeName.lastIndexOf(".")+1);
	}
	
	//该函数用于把 parent.children.parent变为 getcn
	public static String getFirstArg(String args)
	{
		if(args.indexOf(".")!=-1)
			return args.substring(0, args.indexOf("."));
		else
			return args;
	}
	
	public static String replaceByRegexFromArgs(String context,String[] args)
	{
		String regex="\\{\\w*\\}";
		if(args!=null&&args.length!=0)
		{
			for(String value:args)
			{
				context=context.replaceFirst(regex, value);
			}
		}
		return context;
	}
	
	public static String deleteKuoHao(String context)
	{
		context= context.replace("{","");
		context= context.replace("}","");
		return context;
	}
	
	public static String changeFirstCharToLow(String oldString)
	{
		String newString=null;
		newString=oldString.substring(0, 1).toLowerCase()+oldString.substring(1);
		return newString;
	}
	
	public static String changeFirstCharToUp(String oldString)
	{
		String newString=null;
		newString=oldString.substring(0, 1).toUpperCase()+oldString.substring(1);
		return newString;
	}
	
	
	//该函数用于把 cn.simple.entity.duty变为  cn\\simple\\entity\\duty
	public static String changePackageNameToFloder(String oldString)
	{
		return oldString.replace('.', '\\');
	}
	
	
	//该函数用于把class ddd.simple.entity.test.ClassesTest变为  ddd.simple.entity.test.ClassesTest
	public static String getRealReturnTypeName(String oldString)
	{
		return oldString.substring(oldString.indexOf(' ')+1);
	}
	
	
	//该函数用于把java.util.List<ddd.cam.entity.provider.ProviderType>变为 ddd.cam.entity.provider.ProviderType
	public static String getGenericReturnTypeName(String oldString)
	{
		return oldString.substring(oldString.indexOf('<')+1, oldString.lastIndexOf('>'));
	}
	
	
	//如果传入的是泛型，则调用getGenericReturnTypeName返回<>里的类型名，反之返回类型
	public static String getGenericTypeToString(String oldType)
	{
		if(oldType.indexOf("<")!=-1&&oldType.indexOf(">")!=-1)
		{
			return getGenericReturnTypeName(oldType);
		}
		return getRealReturnTypeName(oldType);
	}
	
	
	//该函数用于判断反射得来的函数是否为一个类私有属性的get方法
	public static boolean methodIsGet(String methodName,String methodReturnType)
	{
		if(methodName.startsWith("is")||methodName.startsWith("get"))
		{
			if(!methodReturnType.equals("void"))
			{
				return true;
			}
		}
		return false;
	}
}
