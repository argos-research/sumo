function packedData = packInt64(data)
%packInt32 Internal function to cast an int64 into 
%	an uint8 array

%   Copyright 2014 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: packInt64.m 19 2014-05-30 15:08:25Z afacostag $

packedData = fliplr(typecast(data,'uint8'));