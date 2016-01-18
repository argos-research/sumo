function packedData = packInt32(data)
%packInt32 Internal function to cast an int32 into 
%	an uint8 array

%   Copyright 2014 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: packInt32.m 19 2014-05-30 15:08:25Z afacostag $

if isa(data,'int32')
	packedData = fliplr(typecast(data,'uint8'));
else
	packedData = fliplr(typecast(int32(data),'uint8'));
end