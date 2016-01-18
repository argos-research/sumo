function minGap = getMinGap(personID)
%getMinGap Returns the gap to front person.
%   minGap = getMinGap(PERSONID) Returns the offset (gap to front person if 
%   halting) of this person.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getMinGap.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
minGap = traci.person.getUniversal(constants.VAR_MINGAP, personID);