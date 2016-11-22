function minGap = getMinGap(typeID)
%getMinGap Returns the offset of vehicles of this type.
%   minGap = getMinGap(TYPEID) Returns the offset (gap to front vehicle if 
%   halting) of vehicles of this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getMinGap.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
minGap = traci.vehicletype.getUniversal(constants.VAR_MINGAP, typeID);